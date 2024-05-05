package model.Dto;

public class Guest_ReviewDto {
    private int review_pk; // 리뷰번호 primary key
    private int target; // 대상
    private int writer; // 쓴사람
    private String content; // 내용
    private int score; // 점수

    public Guest_ReviewDto(){}

    public Guest_ReviewDto(int review_pk, int target, int writer, String content, int score) {
        this.review_pk = review_pk;
        this.target = target;
        this.writer = writer;
        this.content = content;
        this.score = score;
    }

    public int getReview_pk() {
        return review_pk;
    }

    public void setReview_pk(int review_pk) {
        this.review_pk = review_pk;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getWriter() {
        return writer;
    }

    public void setWriter(int writer) {
        this.writer = writer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
