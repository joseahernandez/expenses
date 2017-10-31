var path = require('path');

const PATH = {
    app: path.join(__dirname, 'src/main/resources/app/app.js'),
    build: path.join(__dirname, 'src/main/resources/public/js'),
    content: path.join(__dirname, 'src/main/resources/public')
};

module.exports = {
    entry: {
        app: PATH.app
    },
    output: {
        path: PATH.build,
        filename: '[name].js'
    },

    module: {
        rules: [
            {
                test: /\.js$/,
                loader: ['babel-loader'],
                exclude: /node_modules/
            },
            {
                test: /\.css/,
                loader: ['style-loader', 'css-loader']
            },
            {
                test: /\.(png|jpg|jpeg|gif|svg|woff|woff2|ttf|eot)$/,
                loader: ['file-loader']
            },
            {
                test: /\.html$/,
                loader: ['html-loader']
            }
        ]
    },

    devServer:  {
        contentBase: PATH.content,
        stats: 'minimal'
    }
};
