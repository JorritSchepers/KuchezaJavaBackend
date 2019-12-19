package nl.han.oose.sapporo.resource;

import nl.han.oose.sapporo.dto.AllAnimalDTO;
import nl.han.oose.sapporo.dto.UserDTO;
import nl.han.oose.sapporo.service.IAccountService;
import nl.han.oose.sapporo.service.IAdminService;
import nl.han.oose.sapporo.service.IAnimalService;
import nl.han.oose.sapporo.service.IPlotService;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class AnimalResourceTest {
    private String token = "1234567890";
    private AnimalResource sut = new AnimalResource();
    private IAccountService accountService = Mockito.mock(IAccountService.class);
    private IAnimalService animalService = Mockito.mock(IAnimalService.class);
    private AllAnimalDTO allAnimals = new AllAnimalDTO(null);
    private IAdminService adminService = Mockito.mock(IAdminService.class);
    private IPlotService plotService = Mockito.mock(IPlotService.class);
    private UserDTO user = new UserDTO();

    public AnimalResourceTest() {
        sut.setAccountService(accountService);
        sut.setAnimalService(animalService);
        sut.setPlotService(plotService);
        sut.setAdminService(adminService);
        Mockito.when(accountService.verifyToken(token)).thenReturn(user);
        Mockito.when(animalService.getAllAnimals()).thenReturn(allAnimals);
    }

    @Test
    public void getAllAnimalsCallsAuthenticateByToken() {
        sut.getAllAnimals(token);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    public void getAllAnimalsCallsGetAllAnimals() {
        sut.getAllAnimals(token);
        Mockito.verify(animalService, Mockito.times(1)).getAllAnimals();
    }

    @Test
    public void getAllAnimalsReturnsRightAllAnimal() {
        Assertions.assertEquals(allAnimals, sut.getAllAnimals(token).getEntity());
    }

    @Test
    public void deleteAnimalCallsVerifyToken(){
        final int IDDELETE = 1;
        final int IDREPLACE =2;
        sut.deleteAnimal(token,IDDELETE,IDREPLACE);
        Mockito.verify(accountService, Mockito.times(1)).verifyToken(token);
    }

    @Test
    public void deleteAnimalcheckIfUserIsAdmin(){
        final int IDDELETE = 1;
        final int IDREPLACE =2;
        sut.deleteAnimal(token,IDDELETE,IDREPLACE);
        Mockito.verify(adminService, Mockito.times(1)).checkIfUserIsAdmin(user);
    }

    @Test
    public void deleteAnimalCallreplaceAnimalsOnAllPlots(){
        final int IDDELETE = 1;
        final int IDREPLACE =2;
        sut.deleteAnimal(token,IDDELETE,IDREPLACE);
        Mockito.verify(plotService, Mockito.times(1)).replaceAnimalsOnAllPlots(IDDELETE,IDREPLACE);
    }

    @Test
    public void deleteAnimalCallsdeleteAnimal(){
        final int IDDELETE = 1;
        final int IDREPLACE =2;
        sut.deleteAnimal(token,IDDELETE,IDREPLACE);
        Mockito.verify(animalService, Mockito.times(1)).deleteAnimal(IDDELETE);
    }
}
