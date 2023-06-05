package lk.ijse.palmoilfactory.entity;

import java.io.Serializable;

//To security of T → Only pass sub entities to super entity in CrudDAO
public interface SuperEntity extends Serializable { //save object in Hard Drive(Serializable interface)
    //Serializable interface --> Marker Interface

}
