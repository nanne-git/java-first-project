package Service;

import model.IRoom;

import java.util.Collection;
import java.util.Date;

public interface ReservationServiceDefaultMethod {
    default Collection<IRoom> recommendedRooms() {
        return null;
    }
}
