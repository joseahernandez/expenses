var path = require('path');
var webpack = require('webpack');

var CommonsChunkPlugin = webpack.optimize.CommonsChunkPlugin;
var autoprefixer = require('autoprefixer');
var HtmlWebpackPlugin = require('html-webpack-plugin');
var ExtractTextPlugin = require('extract-text-webpack-plugin');
var CopyWebpackPlugin = require('copy-webpack-plugin');

var ENV = process.env.npm_lifecycle_event;
var isTest = ENV === 'test' || ENV === 'test-watch';
var isProd = ENV === 'build';

module.exports = function makeWebpackConfig() {
    var config = {};

    config.entry = isTest ? void 0 : {
        app: './src/main/resources/app/app.js'
    };

    config.output = isTest ? {} : {
        path: __dirname + '/dist',

        publicPath: isProd ? '/' : 'http://localhost:3000/',

        filename: isProd ? '[name].[hash].js' : '[name].bundle.js',

        chunkFilename: isProd ? '[name].[hash].js' : '[name].bundle.js'
    };

    if (isTest) {
        config.devtool = 'inline-source-map';
    }
    else if (isProd) {
        config.devtool = 'source-map';
    }
    else {
        config.devtool = 'eval-source-map';
    }

    config.module = {
        rules: [{
            // JS LOADER
            // Reference: https://github.com/babel/babel-loader
            // Transpile .js files using babel-loader
            // Compiles ES6 and ES7 into ES5 code
            test: /\.js$/,
            loader: 'babel-loader',
            exclude: /node_modules/
        }, {
            // CSS LOADER
            // Reference: https://github.com/webpack/css-loader
            // Allow loading css through js
            //
            // Reference: https://github.com/postcss/postcss-loader
            // Postprocess your css with PostCSS plugins
            test: /\.css$/,
            // Reference: https://github.com/webpack/extract-text-webpack-plugin
            // Extract css files in production builds
            //
            // Reference: https://github.com/webpack/style-loader
            // Use style-loader in development.

            loader: isTest ? 'null-loader' : ExtractTextPlugin.extract({
                fallbackLoader: 'style-loader',
                loader: [
                    {loader: 'css-loader', query: {sourceMap: true}},
                    {loader: 'postcss-loader'}
                ],
            })
        }, {
            // ASSET LOADER
            // Reference: https://github.com/webpack/file-loader
            // Copy png, jpg, jpeg, gif, svg, woff, woff2, ttf, eot files to output
            // Rename the file using the asset hash
            // Pass along the updated reference to your code
            // You can add here any file extension you want to get copied to your output
            test: /\.(png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot)$/,
            loader: 'file-loader'
        }, {
            // HTML LOADER
            // Reference: https://github.com/webpack/raw-loader
            // Allow loading html through js
            test: /\.html$/,
            loader: 'raw-loader'
        }]
    };

    if (isTest) {
        config.module.rules.push({
            enforce: 'pre',
            test: /\.js$/,
            exclude: [
                /node_modules/,
                /\.spec\.js$/
            ],
            loader: 'istanbul-instrumenter-loader',
            query: {
                esModules: true
            }
        })
    }

    config.plugins = [
        new webpack.LoaderOptionsPlugin({
            test: /\.scss$/i,
            options: {
                postcss: {
                    plugins: [autoprefixer]
                }
            }
        })
    ];

    if (!isTest) {
        // Reference: https://github.com/ampedandwired/html-webpack-plugin
        // Render index.html
        config.plugins.push(
            new HtmlWebpackPlugin({
                template: './src/main/resources/public/index.html',
                inject: 'body'
            }),

            // Reference: https://github.com/webpack/extract-text-webpack-plugin
            // Extract css files
            // Disabled when in test mode or not in build mode
            new ExtractTextPlugin({filename: 'css/[name].css', disable: !isProd, allChunks: true})
        )
    }

    if (isProd) {
        config.plugins.push(
            // Reference: http://webpack.github.io/docs/list-of-plugins.html#noerrorsplugin
            // Only emit files when there are no errors
            new webpack.NoErrorsPlugin(),

            // Reference: http://webpack.github.io/docs/list-of-plugins.html#dedupeplugin
            // Dedupe modules in the output
            new webpack.optimize.DedupePlugin(),

            // Reference: http://webpack.github.io/docs/list-of-plugins.html#uglifyjsplugin
            // Minify all javascript, switch loaders to minimizing mode
            new webpack.optimize.UglifyJsPlugin(),

            // Copy assets from the public folder
            // Reference: https://github.com/kevlened/copy-webpack-plugin
            new CopyWebpackPlugin([{
                from: __dirname + '/src/main/resources/public'
            }])
        )
    }

    config.devServer = {
        contentBase: './src/main/resources/public',
        stats: 'minimal'
    };

    return config;
}();
