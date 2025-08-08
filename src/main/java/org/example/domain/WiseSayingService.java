package org.example.domain;

import java.util.List;
import org.example.WiseSayingRepository;

public class WiseSayingService {
//역할 : 순수 비지니스 로직
//스캐너 사용금지, 출력 금지

    private final WiseSayingRepository repository;
    private int nextId = 1; // 명언 ID 자동 증가를 위한 변수

    public WiseSayingService(WiseSayingRepository repository) {
        this.repository = repository;
    }


    // 명언 저장
    public WiseSaying saveWiseSaying(String content, String author) {
        WiseSaying wiseSaying = new WiseSaying(nextId++, content, author);
        repository.save(wiseSaying);
        return wiseSaying;
    }

    // 명언 전체 조회
    public List<WiseSaying> getAllWiseSayings() {
        return repository.findAll();
    }

    // 단일 조회
    public WiseSaying getWiseSayingById(int id) {
        return repository.findById(id);
    }

    //수정
    public boolean updateWiseSaying(WiseSaying wiseSaying) {
        return repository.update(wiseSaying);
    }

    // 삭제
    public boolean deleteWiseSayingById(int id) {
        return repository.deleteById(id);
    }
}
