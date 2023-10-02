package project.shop.entity.product;

import lombok.Getter;

import java.util.*;

@Getter
public enum ProductCategory {

    VEGETABLE("채소", null),
    FRUIT("과일", null),
    SEAFOOD("수산", null),
        FISH("생선", SEAFOOD),
    MEAT("정육", null),
        BEEF("소고기", MEAT),
        PORK("돼지고기", MEAT),
        CHICKEN("닭고기", MEAT),
        EGG("계란", MEAT),
    DRINK("음료", null),
        WATER("생수", DRINK),
        JUICE("주스", DRINK),
        MILK("우유", DRINK),
        TEA("차", DRINK),
        COFFEE("커피", DRINK),
    BAKERY("베이커리", null),
        BREAD("빵", BAKERY),
        JAM("잼/스프레드", BAKERY),
    SNACK("간식", null),
        COOKIE("과자/쿠키", SNACK),
        CHOCOLATE("초콜릿/캔디", SNACK),
        ICECREAM("아이스크림", SNACK)
    ;

    private final String title;
    private final ProductCategory parentCategory;
    private final List<ProductCategory> childCategories;

    ProductCategory(String title, ProductCategory parentCategory) {
        this.title = title;
        this.parentCategory = parentCategory;
        this.childCategories = new ArrayList<>();
        if(Objects.nonNull(parentCategory)) { // 부모 카테고리가 null이 아닌 경우
            parentCategory.childCategories.add(this);
        }
    }

    // 상위 카테고리 모두 조회
    public List<ProductCategory> getParentCategories() {

        List<ProductCategory> parentCategories = new ArrayList<>();
        for(ProductCategory c = this; c.getParentCategory() != null; c = c.getParentCategory()) {
            parentCategories.add(c.getParentCategory());
        }
        return parentCategories;
    }

    // 하위 카테고리 모두 조회
    public List<ProductCategory> getChildCategories() {
        // 자식 카테고리의 읽기 전용 리스트 반환
        return this.getChildCategories();
    }
}
