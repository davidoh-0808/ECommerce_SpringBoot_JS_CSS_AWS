package com.application.gentlegourmet.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

//not entity but a DTO for product search form
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductSearch {
/*
    <!-- Search-->
    <form id="searchBarForm" th:action="@{/search/}" th:object="${productSearch}" method="post"
          class="input-group d-none d-lg-flex flex-nowrap mx-4">
        <i class="ci-search position-absolute top-50 start-0 translate-middle-y ms-3"></i>
        <input th:field="*{keyword}" id="searchBar" class="form-control rounded-start w-25" type="text"
               placeholder="Search for products">
        <select th:field="*{category}" class="form-select flex-shrink-0" style="width: 10rem;">
            <option value="all">All Categories</option>
            <option value="meat">비건 대체 육류</option>
            <option value="dairy">비건 대체 유제품</option>
            <option value="snack">비건 스낵</option>
            <option value="condiment">비건 소스/조미료</option>
        </select>
    </form>
 */
    private String category;
    private String keyword;

}
