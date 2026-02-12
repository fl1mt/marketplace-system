package com.marketplace.product;
import com.marketplace.product.ProductType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;
@Entity
@Table(name = "products")
public class Product{
        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID id;
        @Column(nullable = false)
        private String name;
        @Column(columnDefinition = "TEXT", nullable = false)
        private String description;
        @Column(nullable = false)
        @Enumerated(EnumType.STRING)
        private ProductType category;
        @Column(nullable = false)
        private Integer discountPercentage;
        @Column(nullable = false)
        private Integer inventoryStock;
        @Column(nullable = false)
        private BigDecimal averageRating = BigDecimal.ZERO;
        @Column(nullable = false)
        private Long reviewCount = 0L;
        @Column(nullable = false)
        private BigDecimal basePrice;
        private BigDecimal finalPrice;
        @Version
        private Long version;

        public void setId(UUID id) {
                this.id = id;
        }

        public UUID getId() {
                return id;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getName() {
                return name;
        }

        public void setDescription(String description) {
                this.description = description;
        }

        public String getDescription() {
                return description;
        }

        public void setDiscountPercentage(Integer discountPercentage) {
                this.discountPercentage = discountPercentage;
        }

        public Integer getDiscountPercentage() {
                return discountPercentage;
        }

        public void setInventoryStock(Integer inventoryStock) {
                this.inventoryStock = inventoryStock;
        }

        public Integer getInventoryStock() {
                return inventoryStock;
        }

        public void setAverageRating(BigDecimal averageRating) {
                this.averageRating = averageRating;
        }

        public BigDecimal getAverageRating() {
                return averageRating;
        }

        public void setReviewCount(Long reviewCount) {
                this.reviewCount = reviewCount;
        }

        public Long getReviewCount() {
                return reviewCount;
        }

        public void setBasePrice(BigDecimal basePrice) {
                this.basePrice = basePrice;
        }

        public BigDecimal getBasePrice() {
                return basePrice;
        }

        public void setFinalPrice(BigDecimal finalPrice) {
                this.finalPrice = finalPrice;
        }

        public BigDecimal getFinalPrice() {
                return finalPrice;
        }

        public void setCategory(ProductType category) {
                this.category = category;
        }

        public ProductType getCategory() {
                return category;
        }

        public void setVersion(Long version) {
                this.version = version;
        }

        public Long getVersion() {
                return version;
        }
}
