package course.android.letgo_300432317_303062210.utils;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * NetworkResListener interface
 */
public interface NetworkResListener {
    /**
     * callback method which called when the resources update is started
     */
    public void onPreUpdate();

    /**
     * callback method which called after resources update is finished
     * @param  res  - the data
     * @param status - the status of the update process
     */
    public void onProductUpdate(byte[] res, ResStatus status);

    public void onProductUpdate(JSONObject res, ResStatus status);

    public void onProductUpdate(Bitmap res, ResStatus status);

    public void onUserUpdate(JSONObject res,ResStatus status);

}
