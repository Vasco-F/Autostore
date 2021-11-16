// package com.example.models;

// import javax.persistence.CascadeType;
// import javax.persistence.Column;
// import javax.persistence.Entity;
// import javax.persistence.GeneratedValue;
// import javax.persistence.GenerationType;
// import javax.persistence.Id;
// import javax.persistence.OneToOne;
// import javax.persistence.PrimaryKeyJoinColumn;
// import javax.persistence.Table;

// // Class currently not used

// @Entity
// @Table(name = "images")
// public class Image {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long vehicle_id;


//     @OneToOne(cascade = CascadeType.MERGE, optional = false)
//     @PrimaryKeyJoinColumn(name = "vehicle_id", referencedColumnName = "id")
//     private Vehicle vehicle;

//     @Column(unique = true)
//     private String path;

//     public Image(){}

//     public Image(Vehicle vehicle, String path){
//         this.vehicle = vehicle;
//         this.path = path;
//     }

//     public void setVehicle(Vehicle vehicle){
//         this.vehicle = vehicle;
//     }

//     public void setPath(String path){
//         this.path = path;
//     }

//     public Vehicle getVehicle(){
//         return this.vehicle;
//     }

//     public String getPath(){
//         return this.path;
//     }
// }
