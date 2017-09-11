package vn.stage.dental_manager.data;

import retrofit2.http.GET;
import rx.Observable;
import vn.stage.dental_manager.data.model.ResponseModel;

/**
 * Created by congn on 9/11/2017.
 */

public interface MyEndPoint {
    @GET("/getTokenAdmin")
    Observable<ResponseModel> getTokenAdmin();
}
