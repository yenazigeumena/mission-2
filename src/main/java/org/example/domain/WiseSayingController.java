package org.example.domain;

import java.util.Scanner;

public class WiseSayingController {
    //역할 : 고객의 명령을 입력받고 적절을 응답을 표현
    //이 단계에서는 스캐너 사용가능
    //이 단계에서는 출력 사용가능

    private final WiseSayingService service;
    private final Scanner sc = new Scanner(System.in);

    public WiseSayingController(WiseSayingService service) {
        this.service = service;
    }



    // 명언 등록
    public void register() {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();
        WiseSaying wiseSaying = service.saveWiseSaying(content, author);
        System.out.println(wiseSaying.getId() + "번 명언이 등록되었습니다.");
    }

    // 명언 전체 조회
    public void listAll() {
        System.out.println("번호 / 작가 / 명언 ");
        for (WiseSaying wiseSaying : service.getAllWiseSayings()) {
            System.out.println(wiseSaying.getId() + " / " + wiseSaying.getContent() + " / " + wiseSaying.getAuthor());
        }
    }

    //삭제
    public void delete(int id) {
        boolean isDeleted = service.deleteWiseSayingById(id);
        if (isDeleted) {
            System.out.println(id + "번 명언이 삭제되었습니다.");
        } else {
            System.out.println("해당 번호의 명언이 없습니다.");
        }
    }

    //수정
    public void update(int id) {
        WiseSaying saying = service.getWiseSayingById(id);
        if (saying == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        } else {
            System.out.println("명언(기존) : " + saying.getContent());
            System.out.print("명언 : ");
            String content = sc.nextLine();

            System.out.println("작가(기존) : " + saying.getAuthor());
            System.out.print("작가 : ");
            String author = sc.nextLine();

            service.updateWiseSaying(new WiseSaying(id, content, author));
            System.out.println(id + "번 명언이 수정되었습니다.");
        }
    }
}
