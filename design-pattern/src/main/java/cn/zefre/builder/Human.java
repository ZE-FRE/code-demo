package cn.zefre.builder;

import lombok.ToString;

/**
 * @author pujian
 * @date 2021/3/25 10:18
 */
@ToString
public class Human {

    private String head;

    private String body;

    private String footer;

    public Human() {}

    private Human(Builder builder) {
        this.head = builder.head;
        this.body = builder.body;
        this.footer = builder.footer;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String head;
        private String body;
        private String footer;

        public Builder setHead(String head) {
            this.head = head;
            return this;
        }
        public Builder setBody(String body) {
            this.body = body;
            return this;
        }
        public Builder setFooter(String footer) {
            this.footer = footer;
            return this;
        }
        public Human build() {
            return new Human(this);
        }
    }
}
