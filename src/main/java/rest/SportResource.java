package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.AddressRequestDTO;
import dto.PostnordResponseDTO;
import dto.ServicepointsResponseDTO;
import dto.SportResponseDTO;
import dto.WeatherResponseDTO;
import utils.EMF_Creator;
import facades.FacadeExample;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import utils.Helper;
import utils.HttpUtils;
//import utils.Keys;

@Path("predictions")
public class SportResource {

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
       
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final String sportURL = "https://football-prediction-api.p.rapidapi.com/api/v2/predictions";
    private static final ExecutorService es = Executors.newCachedThreadPool();
    
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed({"user", "admin"})
    public String getServicePointAndWeather() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        return responseFromExternalServersParallel(es);
    }
    
    public static String responseFromExternalServersParallel(ExecutorService threadPool) throws InterruptedException, ExecutionException, TimeoutException 
    {
        Callable<SportResponseDTO> sportTask = new Callable<SportResponseDTO>() {
            @Override
            public SportResponseDTO call() throws IOException {
                String sport = HttpUtils.fetchData(sportURL);
                SportResponseDTO sportDTO = gson.fromJson(sport, SportResponseDTO.class);
                return sportDTO;
            }
        };
        
        Future<SportResponseDTO> futureSport = threadPool.submit(sportTask);
        
        SportResponseDTO sport = futureSport.get(3, TimeUnit.SECONDS);
        
        String sportJSON = gson.toJson(sport);
        
        return sportJSON;
    }
  
    
}
