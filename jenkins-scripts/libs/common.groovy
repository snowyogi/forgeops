/*
 * Copyright 2019 ForgeRock AS. All Rights Reserved
 *
 * Use of this code requires a commercial software license with ForgeRock AS.
 * or with one of its affiliates. All use shall be exclusively subject
 * to such license between the licensee and ForgeRock AS.
 */

/*
 * Common configuration used by several stages of the ForgeOps pipeline.
 */

/**
 * Globally scoped git commit information
 */
FORGEOPS_SHORT_GIT_COMMIT = sh(script: 'git rev-parse --short HEAD', returnStdout: true).trim()

/**
 * Globally scoped git commit information
 */
FORGEOPS_GIT_COMMIT = sh(script: 'git rev-parse HEAD', returnStdout: true).trim()

/**
 * Globally scoped git commit information for the Lodestar repo
 */
LODESTAR_GIT_COMMIT = '62231740ef16aaea9a9d6fdef40fc4cc7f210b8b'

/**
 * Base versions for the PIT#2 upgrade test
 */
UPGRADE_TEST_BASE_AMSTER_VERSION      = '7.0.0-a220039d37'
UPGRADE_TEST_BASE_AM_VERSION          = '7.0.0-a220039d37'
UPGRADE_TEST_BASE_IDM_VERSION         = '7.0.0-67e54db'
UPGRADE_TEST_BASE_CONFIGSTORE_VERSION = '7.0.0-bdc0ce8'
UPGRADE_TEST_BASE_USERSTORE_VERSION   = '7.0.0-bdc0ce8'

/**
 * Helm chart file path, and data relevant to the ForgeOps pipeline.
 */
HELM_CHARTS = [
        'am' : ['filePath': 'helm/openam/values.yaml',  'rootLevelImageName': 'gcr.io/forgerock-io/am'],
        'amster': ['filePath': 'helm/amster/values.yaml', 'rootLevelImageName': 'gcr.io/forgerock-io/amster'],
        'ds' : ['filePath': 'helm/ds/values.yaml',      'rootLevelImageName': 'gcr.io/forgerock-io/ds'],
        'idm': ['filePath': 'helm/openidm/values.yaml', 'rootLevelImageName': 'gcr.io/forgerock-io/idm'],
        'ig' : ['filePath': 'helm/openig/values.yaml',  'rootLevelImageName': 'gcr.io/forgerock-io/ig'],
]

def loadCurrentHelmChartValues() {
    assertUsingNode()
    HELM_CHARTS.each { product, helmChart ->
        def helmChartYaml = readYaml file: helmChart.filePath
        helmChart.currentImageName = helmChartYaml.image.repository
        helmChart.currentTag = helmChartYaml.image.tag
    }
}

return this