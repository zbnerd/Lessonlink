package com.lessonlink.dto;

import lombok.Getter;

@Getter
public class PostDto {
    private String title;
    private Boolean isNotice;
    private String contents;

    private PostDto(Builder builder) {
        this.title = builder.title;
        this.isNotice = builder.isNotice;
        this.contents = builder.contents;
    }

    public static class Builder {
        private String title;
        private Boolean isNotice;
        private String contents;

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder isNotice(Boolean isNotice) {
            this.isNotice = isNotice;
            return this;
        }

        public Builder contents(String contents) {
            this.contents = contents;
            return this;
        }

        public PostDto build() {
            return new PostDto(this);
        }
    }
}
