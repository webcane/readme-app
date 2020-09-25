const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
// const bootstrapSocial = require('bootstrap-social');
// const fontAwesome = require('font-awesome');
const webpack = require('webpack');
const path = require('path');

const mode = process.env.NODE_ENV || 'development';
const prod = mode === 'production';

module.exports = {
    entry: {
        bundle: ['./src/main/svelte/main.js'],
    },
    output: {
        path: __dirname + '/src/main/resources/public/build',
        filename: '[name].js',
        chunkFilename: '[name].[id].js'
    },
    mode,
    devtool: prod ? false : 'source-map',
    devServer: {
        port: 5000,
        contentBase: path.join(__dirname, 'src/main/resources/public'),
        clientLogLevel: prod ? 'info' : 'debug',
        overlay: true,
        proxy: {
            '/svc': {
                target: 'http://localhost:3000',
                pathRewrite: {'^/svc': ''},
                changeOrigin: true
            }
        }
    },
    resolve: {
        alias: {
            'svelte': path.resolve('node_modules', 'svelte'),
            'svelte-tags-input':path.resolve('node_modules', 'svelte-tags-input'),
        },
        extensions: ['.mjs', '.js', '.svelte', '.json'],
        mainFields: ['svelte', 'browser', 'module', 'main'],
    },
    module: {
        rules: [
            {
                test: /\.svelte$/,
                use: {
                    loader: 'svelte-loader',
                    options: {
                        emitCss: true,
                        hotReload: true,
                    },
                },
            },
            {
                test: /\.css$/,
                use: [
                    /**
                     * MiniCssExtractPlugin doesn't support HMR.
                     * For developing, use 'style-loader' instead.
                     * */
                    prod ? MiniCssExtractPlugin.loader : 'style-loader',
                    'css-loader',
                ],
            },
        ],
    },
    plugins: [
        new webpack.LoaderOptionsPlugin({
            debug: !prod
        }),
        new MiniCssExtractPlugin({
            filename: '[name].css',
        }),
        // minify index.html
        // new HtmlWebpackPlugin({
        //     title: 'ReadmeApp',
        //     template: 'src/main/resources/public/index.html',
        // }),
    ]
};