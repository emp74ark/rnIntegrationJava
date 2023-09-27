const {getDefaultConfig, mergeConfig} = require('@react-native/metro-config');
const path = require('path');

const projectRoot = __dirname;

const config = getDefaultConfig(projectRoot);

config.resolver.nodeModulesPaths = [
    path.resolve(projectRoot, 'node_modules'),
];

config.resolver.disableHierarchicalLookup = true;

module.exports = config;
