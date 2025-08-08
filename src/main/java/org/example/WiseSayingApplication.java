package org.example;

import java.util.Scanner;
import org.example.domain.WiseSayingController;
import org.example.domain.WiseSayingService;

public class WiseSayingApplication {
    public static void main(String[] args) {

        WiseSayingRepository repository = new WiseSayingRepository();
        WiseSayingService service = new WiseSayingService(repository);
        WiseSayingController controller = new WiseSayingController(service);
        Scanner sc = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String cmd = sc.nextLine().trim();

            if ("등록".equals(cmd)) {
                controller.register();
            }
            else if ("목록".equals(cmd)) {
                controller.listAll();
            }
            else if (cmd.startsWith("삭제?id=")) {
                int id = Integer.parseInt(cmd.substring("삭제?id=".length()));
                controller.delete(id);
            }
            else if (cmd.startsWith("수정?id=")) {
                int id = Integer.parseInt(cmd.substring("수정?id=".length()));
                controller.update(id);
            }
            else if ("종료".equals(cmd)) {
                System.out.println("프로그램을 종료합니다.");
                break;
            }
            else {
                System.out.println("알 수 없는 명령입니다.");
            }
        }

        sc.close();
    }



}