/*
*Copyright (c) 2005-2010, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*WSO2 Inc. licenses this file to you under the Apache License,
*Version 2.0 (the "License"); you may not use this file except
*in compliance with the License.
*You may obtain a copy of the License at
*
*http://www.apache.org/licenses/LICENSE-2.0
*
*Unless required by applicable law or agreed to in writing,
*software distributed under the License is distributed on an
*"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
*KIND, either express or implied.  See the License for the
*specific language governing permissions and limitations
*under the License.
*/

package org.wso2.carbon.apimgt.tokenmgt.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.apimgt.impl.APIConstants;
import org.wso2.carbon.apimgt.impl.APIManagerConfiguration;
import org.wso2.carbon.apimgt.impl.APIManagerConfigurationService;
import org.wso2.carbon.apimgt.common.gateway.dto.JWTConfigurationDto;
import org.wso2.carbon.registry.core.service.RegistryService;
import org.wso2.carbon.user.core.service.RealmService;

public class TokenMgtDataHolder {

    private static RegistryService registryService;
    private static RealmService realmService;
    private static APIManagerConfigurationService amConfigService;
    private static Boolean isKeyCacheEnabledKeyMgt = true;
    private static final Log log = LogFactory.getLog(TokenMgtDataHolder.class);

    // Scope used for marking Application Tokens
    private static String applicationTokenScope;

    public static Boolean getKeyCacheEnabledKeyMgt() {
        return isKeyCacheEnabledKeyMgt;
    }

    public static void setKeyCacheEnabledKeyMgt(Boolean keyCacheEnabledKeyMgt) {
        isKeyCacheEnabledKeyMgt = keyCacheEnabledKeyMgt;
    }

    public static void setAmConfigService(APIManagerConfigurationService amConfigService) {
        TokenMgtDataHolder.amConfigService = amConfigService;
    }

    public static RegistryService getRegistryService() {
        return registryService;
    }

    public static void setRegistryService(RegistryService registryService) {
        TokenMgtDataHolder.registryService = registryService;
    }

    public static RealmService getRealmService() {
        return realmService;
    }

    public static void setRealmService(RealmService realmService) {
        TokenMgtDataHolder.realmService = realmService;
    }

    public static void initData() {
        try {

            APIManagerConfiguration configuration = org.wso2.carbon.apimgt.impl.internal.ServiceReferenceHolder.getInstance()
                    .getAPIManagerConfigurationService().getAPIManagerConfiguration();

            if (configuration == null) {
                log.error("API Manager configuration is not initialized");
            } else {

                applicationTokenScope = configuration.getFirstProperty(APIConstants
                                                                               .APPLICATION_TOKEN_SCOPE);
                JWTConfigurationDto jwtConfigurationDto = configuration.getJwtConfigurationDto();
                if (log.isDebugEnabled()) {
                    log.debug("JWTGeneration enabled : " + jwtConfigurationDto.isEnabled());
                }

            }
        } catch (Exception e) {
            log.error("Error occur while initializing API KeyMgt Data Holder.Default configuration will be used." + e.toString());
        }
    }

    public static String getApplicationTokenScope() {
        return applicationTokenScope;
    }

}
