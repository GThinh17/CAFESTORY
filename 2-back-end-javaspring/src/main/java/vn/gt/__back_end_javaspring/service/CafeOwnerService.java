package vn.gt.__back_end_javaspring.service;

import vn.gt.__back_end_javaspring.DTO.CafeOwnerDTO;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerResponse;
import vn.gt.__back_end_javaspring.DTO.CafeOwnerUpdateDTO;

import java.util.List;

public interface CafeOwnerService {
    CafeOwnerResponse createCafeOwner(CafeOwnerDTO dto);

    Boolean isCafeOwner(String userId);

    CafeOwnerResponse getById(String id);

    CafeOwnerResponse getByUserId(String userId);

    CafeOwnerResponse updateCafeOwner(String id, CafeOwnerUpdateDTO dto);

    void deleteCafeOwner(String id);

    List<CafeOwnerResponse> getAllCafeOwners();

    CafeOwnerResponse extendCafeOwner(String cafeOwnerId, CafeOwnerDTO dto);


}
