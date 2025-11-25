//package vn.gt.__back_end_javaspring.entity;
//
//import java.sql.Date;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//@Data
//@Entity
//@Table(name = "payment")
//@NoArgsConstructor
//@AllArgsConstructor
//public class Payment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    @Column(name = "paymentId", length = 36)
//    private String paymentId;
//
//    @Column(name = "productionName")
//    private String name;
//
//    @Column(name = "amount")
//    private Long amount;
//
//    @Column(name = "paymentStatus")
//    private Boolean paymentStatus;
//
//    @Column(name = "status")
//    private Boolean status;
//
//    @Column(name = "createAt")
//    private Date createAt;
//
//    @Column(name = "updateAt")
//    private Date updateAt;
//}
