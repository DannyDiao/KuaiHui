
const https = require('https');
const fs_1 = require('fs');
const options = {
    pfx: fs_1.readFileSync('../SSL/diaosudev.cn.pfx'),
    passphrase: '873340a0lc6w5'
  };

const CurrencyPath = './Currency';
var express = require('express');
var app = express();
var httpsServer = https.createServer(options,app);

var CurrencyData = fs_1.readFileSync(CurrencyPath,'utf8');

app.get('/KuaiHuiCurrency',function(req,res){
    console.log('received request');
    res.send(CurrencyData);
});

app.get('/KuaiHuiExchange',function(req,res){
  
})
httpsServer.listen(3500);