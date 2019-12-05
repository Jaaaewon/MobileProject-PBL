import * as React from 'react';
import { Image, FlatList, Text, View, StyleSheet } from 'react-native';

class MyFlatList extends React.Component{
  render(){
    return (
       <View>
               <FlatList
                    data ={productList}
                    renderItem = {({item})=>{
                        return(
                          <View style={{flexDirection: 'row'}}>
                           
                            <Image source={{uri:item.imagePath}} style={{height:200,width:200,resizeMode:'contain'}}/>
                            <View style={{marginTop: 40}}>
                              <Text>{item.name}</Text>
                              <Text>{item.price}</Text>
                            </View>
                          </View>
                        );
                    }}
               />
           </View>
    );
  }
}

export default class App extends React.Component {
  render() {
    return ( 
      <View  style={{felx:1, marginTop: 30 ,justifyContent:"center"} }>
      <MyFlatList/>
      </View>
    );
  }
}

const productList = [
    {
        "name" :"모자",
        "price" :"8,000",
        "itemDescription" : "this is cup of cream tea",
        "imagePath" : "https://image.msscdn.net/images/goods_img/20160521/357178/357178_7_500.jpg"
    },
    {
        "name" :"상의",
        "price" :"22,000",
        "itemDescription" : "this is blue jean",
        "imagePath" : "https://assets.styleship.com/fila/data/productimages/a/3/FK2POB4401X_DGR.jpg"
    },
    {
        "name" :"바지",
        "price" :"30,000",
        "itemDescription" : "this is long padding",
        "imagePath" : "http://m.modmartin.co.kr/web/product/big/201504/171_shop1_726390.jpg"
    },
];
