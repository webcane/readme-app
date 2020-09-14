const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const webpack = require('webpack');

const mode = process.env.NODE_ENV || 'development';
const prod = mode === 'production';

module.exports = {
    entry: {
        bundle: ['./src/main.js']
    },
    resolve: {
        alias: {
            svelte: path.resolve('node_modules', 'svelte')
        },
        extensions: ['.mjs', '.js', '.svelte'],
        mainFields: ['svelte', 'browser', 'module', 'main']
    },
    output: {
        path: __dirname + '/public',
        filename: '[name].js',
        chunkFilename: '[name].[id].js'
    },
    module: {
        rules: [
            {
                test: /\.svelte$/,
                use: {
                    loader: 'svelte-loader',
                    options: {
                        emitCss: true,
                        hotReload: true
                    }
                }
            },
            {
                test: /\.css$/,
                use: [
                    /**
                     * MiniCssExtractPlugin doesn't support HMR.
                     * For developing, use 'style-loader' instead.
                     * */
                    prod ? MiniCssExtractPlugin.loader : 'style-loader',
                    'css-loader'
                ]
            }
        ]
    },
    mode,
    plugins: [
        // prints more readable module names in the browser console
        new webpack.NamedModulesPlugin(),

        new MiniCssExtractPlugin({
            filename: '[name].css'
        })
    ],
    devtool: prod ? false : 'source-map',
    devServer: {
        port: 5000,
        proxy: {
            '/svc': {
                target: 'http://localhost:3000',
                pathRewrite: {'^/svc': ''}
            }
        }
    }
};