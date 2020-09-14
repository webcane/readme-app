const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const path = require('path');
const webpack = require('webpack');

const mode = process.env.NODE_ENV || 'development';

module.exports = {
    mode,
    devtool: 'source-map',
    devServer: {
        port: 5000,
        contentBase: 'public',
        proxy: {
            '/svc': {
                target: 'http://localhost:3000',
                pathRewrite: {'^/svc': ''},
                changeOrigin: true
            }
        }
    },
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
                use: ['style-loader', 'css-loader']
            }
        ]
    },
    plugins: [
        // prints more readable module names in the browser console
        new webpack.NamedModulesPlugin(),

        new webpack.LoaderOptionsPlugin({
            debug: true
        }),

        new MiniCssExtractPlugin({
            filename: '[name].css'
        })
    ]
};