package cn.zefre.concurrent.task;

import java.io.File;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.RecursiveTask;

public class FileSizeTask extends RecursiveTask<Long> {

    private File file;

    public FileSizeTask(File file) {
        this.file = file;
    }

    @Override
    protected Long compute() {
        //System.out.println(Thread.currentThread() + " start calculate " + file.getName() + " size!");
        if(null == file || !file.exists()) {
            throw new RuntimeException("文件不存在!");
        }

        if(file.isDirectory()) {
            File[] children = file.listFiles();
            Deque<FileSizeTask> deque = new LinkedList();
            for (File childFile : children) {
                FileSizeTask fileSizeTask = new FileSizeTask(childFile);
                fileSizeTask.fork();
                deque.offer(fileSizeTask);
            }
            long result = 0;
            while (deque.size() > 0) {
                FileSizeTask task = deque.poll();
                result += task.join();
            }
            return result;

        } else {
            System.out.println(file.getName() + " size = " + file.length() + " byte");
            return file.length();
        }
    }

}
