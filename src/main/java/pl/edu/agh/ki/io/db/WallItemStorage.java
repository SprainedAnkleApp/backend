package pl.edu.agh.ki.io.db;

import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import pl.edu.agh.ki.io.api.models.WallItemResponse;
import pl.edu.agh.ki.io.models.wallElements.WallItem;
import pl.edu.agh.ki.io.models.wallElements.WallItemPage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WallItemStorage {
    private final WallItemRepository wallItemRepository;
    private final ReactionsRepository reactionsRepository;
    public WallItemStorage(WallItemRepository wallItemRepository, ReactionsRepository reactionsRepository){
        this.wallItemRepository = wallItemRepository;
        this.reactionsRepository = reactionsRepository;
    }

    public Page<WallItemResponse> findAll(WallItemPage wallItemPage) {
        Sort sort = Sort.by(wallItemPage.getSortDirection(), wallItemPage.getSortBy());

        Pageable pageable = PageRequest.of(wallItemPage.getPageNumber(),
                wallItemPage.getPageSize(), sort);
        Page<WallItem> wallItems = this.wallItemRepository.findAll(pageable);
        List<WallItemResponse> wallItemResponsesList = wallItems.map(wallItem ->
            WallItemResponse.fromWallItemAndReactions(wallItem, this.reactionsRepository.findByIdWallElementID(wallItem.getId()))
        ).stream().collect(Collectors.toList());
        return new PageImpl<>(wallItemResponsesList, pageable, wallItems.getTotalElements());
    }

    public List<WallItemResponse> findAll(){
        return this.wallItemRepository.findAll().stream()
                .map(wallItem -> WallItemResponse.fromWallItemAndReactions(wallItem, this.reactionsRepository.findByIdWallElementID(wallItem.getId()))).
                collect(Collectors.toList());
    }

    public Optional<WallItem> getWallItemById(Long wallItemId) {
        return this.wallItemRepository.findById(wallItemId);
    }

}
