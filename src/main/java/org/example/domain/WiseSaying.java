package org.example.domain;


public class WiseSaying {
//역할 : 명언 객체(번호/명언내용/작가)
//이 파일은 컨트롤러, 서비스, 리포지터티 모두에서 사용가능

    private int id;
    private String content;
    private String author;

    public WiseSaying(int id, String content, String author) {
        this.id = id;
        this.content = content;
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getAuthor() {
        return author;
    }

    public String toJson() {
        return String.format(
                "{\n" +
                        "  \"id\": %d,\n" +
                        "  \"content\": \"%s\",\n" +
                        "  \"author\": \"%s\"\n" +
                        "}",
                this.id,
                this.content.replace("\"", "\\\""),
                this.author.replace("\"", "\\\"")
        );
    }

}
