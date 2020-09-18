package com.exadel.frs.core.trainservice.cache;

import static com.exadel.frs.core.trainservice.repository.FacesRepositoryTest.makeFace;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import lombok.val;
import org.junit.jupiter.api.Test;

public class FaceCollectionTestIT {

    private static final String COLLECTION_ID = "model_key";

    @Test
    void testCreate() {
        val face1 = makeFace("A", COLLECTION_ID);
        val face2 = makeFace("B", COLLECTION_ID);
        val face3 = makeFace("C", COLLECTION_ID);
        val faces = List.of(face1, face2, face3);
        val faceCollection = FaceCollection.buildFromFaces(faces);

        assertThat(faceCollection).isNotNull();
        assertThat(faceCollection.getFaces()).isNotNull();
        assertThat(faceCollection.getFaces()).hasSize(faces.size());
        assertThat(faceCollection.getFacesMap().get(new CachedFace(face1.getFaceName(), face1.getId()))).isEqualTo(0);
        assertThat(faceCollection.getFacesMap().get(new CachedFace(face2.getFaceName(), face2.getId()))).isEqualTo(1);
        assertThat(faceCollection.getFacesMap().get(new CachedFace(face3.getFaceName(), face3.getId()))).isEqualTo(2);
    }

    @Test
    void testAdd() {
        val faces = List.of(makeFace("A", COLLECTION_ID),
                makeFace("B", COLLECTION_ID),
                makeFace("C", COLLECTION_ID));
        val faceCollection = FaceCollection.buildFromFaces(faces);
        val addedFace = makeFace("D", COLLECTION_ID);

        faceCollection.addFace(addedFace);

        assertThat(faceCollection.getFaces()).hasSize(faces.size() + 1);
        assertThat(faceCollection.getFacesMap().get(new CachedFace(addedFace.getFaceName(), addedFace.getId()))).isEqualTo(3);
    }

    @Test
    void testRemove() {
        val face1 = makeFace("A", COLLECTION_ID);
        val face2 = makeFace("B", COLLECTION_ID);
        val face3 = makeFace("C", COLLECTION_ID);
        val faces = List.of(face1, face2, face3);
        val faceCollection = FaceCollection.buildFromFaces(faces);

        faceCollection.removeFace(face1.getId(), face1.getFaceName());

        assertThat(faceCollection.getFaces()).hasSize(faces.size() - 1);
        assertThat(faceCollection.getFacesMap().get(new CachedFace(face2.getFaceName(), face2.getId()))).isEqualTo(0);
        assertThat(faceCollection.getFacesMap().get(new CachedFace(face3.getFaceName(), face3.getId()))).isEqualTo(1);
    }
}
