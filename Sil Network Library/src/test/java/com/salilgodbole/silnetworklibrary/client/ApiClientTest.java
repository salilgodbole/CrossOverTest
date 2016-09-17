package com.salilgodbole.silnetworklibrary.client;

import com.salilgodbole.silnetworklibrary.BuildConfig;
import com.salilgodbole.silnetworklibrary.response.AccessToken;
import com.salilgodbole.silnetworklibrary.response.Place;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by salil on 17/9/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, manifest = "src/debug/AndroidManifest.xml")
public class ApiClientTest {

    @Test
    public void testGetPlaces() throws Exception {
        ApiClient apiClient = Mockito.mock(ApiClient.class);
        AccessToken accessToken = new AccessToken("AYqCb97qqSQ2AbMNjKR5skKQbpOpFE3oLr43A9NDFmABpIAtkgAAAAA");

        List<Place> places = new ArrayList<>();
//
//        doAnswer(new Answer<Void>() {
//            @Override
//            public Void answer(InvocationOnMock invocation) throws Throwable {
//                // Callback is the 2nd argument of the method, hence getArguments()[2];
//                final Callback<SendCodeResponse> callback = (Callback<SendCodeResponse>) invocation.getArguments()[1];
//                callback.success(new SendCodeResponse());
//
//                return null;
//            }
//        }).when(apiClient).getPlaces(eq(accessToken), any(Callback<List<Place>>.class));
    }
}