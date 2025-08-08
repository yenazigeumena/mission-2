package org.example;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.example.domain.WiseSaying;

public class WiseSayingRepository {
//역할 : 데이터의 조회/수정/삭제/생성을 담당
//스캐너 사용금지, 출력 금지
    private final List<WiseSaying> store = new ArrayList<>();

    // 명언 저장
    public void save(WiseSaying wiseSaying) {
        String json = wiseSaying.toJson();
        Path path = Paths.get(BASE_DIR, wiseSaying.getId() + ".json");
        if (wiseSaying.getId() == 0) {
            int nextId = store.size() + 1; // 현재 저장된 명언의 수를 기반으로 ID 생성
            wiseSaying = new WiseSaying(nextId, wiseSaying.getContent(), wiseSaying.getAuthor());
        }
        store.add(wiseSaying);
        try {
            Files.writeString(path, json);
            // 마지막으로 생성된 ID를 파일에 기록
            saveLastId(wiseSaying.getId());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 명언 전체 조회
    public List<WiseSaying> findAll() {
        return new ArrayList<>(store);
    }

    // 단일 조회
    public WiseSaying findById(int id) {
        for (WiseSaying wiseSaying : store) {
            if (wiseSaying.getId() == id) {
                return wiseSaying;
            }
        }
        return null; // 해당 ID의 명언이 없을 경우
    }

    //삭제
    public boolean deleteById(int id){

        return store.removeIf(wiseSaying -> wiseSaying.getId() == id);
    }

    //수정
    public boolean update(WiseSaying wiseSaying) {
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getId() == wiseSaying.getId()) {
                store.set(i, wiseSaying);
                return true; // 수정 성공
            }
        }
        return false; // 해당 ID의 명언이 없을 경우
    }

    private static final String BASE_DIR = "db/wiseSaying";

    public WiseSayingRepository() {
        try {
            Files.createDirectories(Paths.get(BASE_DIR));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 마지막 ID 조회 (변경 없음)
    public int getLastId() {
        Path path = Paths.get(BASE_DIR, "lastId.txt");
        if (!Files.exists(path)) {
            return 0;
        }
        try {
            return Integer.parseInt(Files.readString(path).trim());
        } catch (IOException | NumberFormatException e) {
            return 0;
        }
    }

    // 마지막 ID 저장 (변경 없음)
    private void saveLastId(int id) {
        Path path = Paths.get(BASE_DIR, "lastId.txt");
        try {
            Files.writeString(path, String.valueOf(id));
        } catch (IOException e) {
            System.err.println("lastId.txt 저장 실패: " + e.getMessage());
        }
    }


    private Optional<WiseSaying> fromJson(String jsonStr) {
        try {
            int id = -1;
            String content = "";
            String author = "";

            // 각 라인을 순회하며 key-value를 추출합니다.
            String[] lines = jsonStr.replace("{", "").replace("}", "").split(",\n");
            for (String line : lines) {
                String[] parts = line.split(":", 2);
                String key = parts[0].trim().replace("\"", "");
                String value = parts[1].trim().replace("\"", "").replace(",", "");

                switch (key) {
                    case "id":
                        id = Integer.parseInt(value);
                        break;
                    case "content":
                        content = value;
                        break;
                    case "author":
                        author = value;
                        break;
                }
            }

            if (id != -1) {
                return Optional.of(new WiseSaying(id, content, author));
            }
        } catch (Exception e) {
            // 파싱 중 오류 발생 시 비어있는 Optional 반환
            System.err.println("JSON 파싱 오류: " + e.getMessage());
        }
        return Optional.empty();
    }

}