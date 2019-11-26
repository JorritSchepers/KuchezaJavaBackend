package nl.han.oose.sapporo.persistence;

import nl.han.oose.sapporo.dto.FarmDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.persistence.datasource.ConnectionFactoryImp;

import javax.inject.Inject;

public interface IFarmDAO {
    @Inject
    void setConnectionFactory(ConnectionFactoryImp connectionFactory);

    void checkIfUserHasAFarm(UserDTO userDTO);

    FarmDTO createFarm(FarmDTO farmDTO, UserDTO userDTO);
}
