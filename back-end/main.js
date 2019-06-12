
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
var CurrencyinJson = JSON.parse(CurrencyData);
console.log(CurrencyinJson.result.lists[0].ratenm);
app.get('/KuaiHuiRate',function(req,res){
    console.log('received request');
    var tempData;
    var Datarray=[];
    for(var i=0;CurrencyinJson.result.lists[i] != null;i++){
      tempData = CurrencyinJson.result.lists[i].rate;
      Datarray.push(tempData);
    }
    res.send(Datarray);
});

app.get('/KuaiHuiExchange',function(req,res){
    console.log(req.query.type);
})
httpsServer.listen(3500);