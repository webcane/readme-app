const path = require('path');
const merge = require('webpack-merge');

const CopyWebpackPlugin = require('copy-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');

const mode = process.env.NODE_ENV || 'production';

const TARGET = process.env.npm_lifecycle_event;
const PATHS = {
    source: path.join(__dirname, 'src'),
    output: path.join(__dirname, '../../../target/classes/static')
};

const common = {
    context: PATHS.source,
    entry: {
        'bundle': './main.js'
    }, // [    PATHS.source  ]
        //{ bundle: ['./src/main.js'] },
    output: {
        path: PATHS.output,
        filename: '[name].js'
    },
    resolve: {
        alias: {
            'svelte': path.resolve('node_modules', 'svelte'),
            'svelte-tags-input': path.resolve('node_modules', 'svelte-tags-input'),
            'sveltestrap': path.resolve('node_modules', 'sveltestrap'),
        },
        extensions: ['.mjs', '.js', '.svelte'],
        mainFields: ['svelte', 'browser', 'module', 'main']
    },
    plugins: [
        new CopyWebpackPlugin({
            patterns: [
                {
                    from: path.join(__dirname, '../resources/static'),
                    to: PATHS.output
                },
            ],
        }),
        new MiniCssExtractPlugin({
            filename: '[name].css'
        })
    ]
};

if (TARGET === 'start' || !TARGET) {
    module.exports = merge(common, {
        mode: 'development',
        devtool: 'source-map',
        module: {
            rules: [
                {
                    test: /\.svelte$/,
                    use: {
                        loader: 'svelte-loader',
                        options: {
                            emitCss: true,
                            hotReload: false
                        }
                    }
                },
                {
                    test: /\.css$/,
                    use: ['style-loader', 'css-loader']
                }
            ]
        },
        devServer: {
            hot: true,
            inline: true,
            port: 3000,
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, PUT, DELETE, PATCH, OPTIONS",
                "Access-Control-Allow-Headers": "X-Requested-With, content-type, Authorization, WWW-Authenticate"
            },
            proxy: {
                '/': {
                    target: 'http://localhost:8080',
                    changeOrigin: true
                }
            },
            publicPath: 'http://localhost:3000/',
            historyApiFallback: true
        }
    });
}

if (TARGET === 'build') {
    module.exports = merge(common, {
        mode: 'production',
        module: {
            rules: [
                { test: /\.svelte$/, use: 'svelte-loader' },
                { test: /\.css$/, use: MiniCssExtractPlugin.loader }
            ]
        }
    });
}