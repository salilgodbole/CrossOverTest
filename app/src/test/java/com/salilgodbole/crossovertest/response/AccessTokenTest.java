package com.salilgodbole.crossovertest.response;

import com.salilgodbole.crossovertest.test.BuildConfig;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;


/**
 * Created by salil on 28/8/16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, manifest = "src/debug/AndroidManifest.xml")
public class AccessTokenTest {

    @Test
    public void testFromJson() throws Exception {
        String json = "{" +
                "\"access_token\" : \"abcde-11223-fghij-12345-klmno\"," +
                "\"refresh_token\" : \"klmno-00998-abcde-11223-fghij\"," +
                "\"token_type\" : \"bearer\"," +
                "\"expiry\" : 1234567890" +
                "}";

        AccessToken accessToken = AccessToken.fromJson(json);
        Assert.assertEquals("abcde-11223-fghij-12345-klmno", accessToken.getToken());
        Assert.assertEquals("klmno-00998-abcde-11223-fghij", accessToken.getRefreshToken());
        Assert.assertEquals("bearer", accessToken.getTokenType());
        Assert.assertEquals(1234567890, accessToken.getExpiresIn());
        Assert.assertEquals("bearer abcde-11223-fghij-12345-klmno", accessToken.getHeaderAccessToken());
    }
}