var request = require('request');
const fs = require('fs');
const Currency = './Currency';
const request_path = 'https://sapi.k780.com/?app=finance.rate&scur=USD,HKD,EUR,JPY,GBP,KRW,CAD,AUD,TWD,SGD,THB,MOP,VND,NZD,CHF,PHP&tcur=CNY&appkey=42125&sign=bcb58eb83ab21f84f80881c1f36be84e';

if(fs.existsSync(Currency)){
    fs.unlinkSync(Currency);
    request(request_path,function(err,res,body){
        //console.log(res.body);
        fs.writeFileSync(Currency,res.body);
    })
}

request(request_path,function(err,res,body){
    fs.writeFileSync(Currency,res.body);
});