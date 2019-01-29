/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 * @flow
 * @lint-ignore-every XPLATJSCOPYRIGHT1
 */

import React, {Component} from 'react';
import {Platform, StyleSheet, Text, View} from 'react-native';
import LoanOnAndroid from "./LoanOnAndroid";

const instructions = Platform.select({
  ios: 'Press Cmd+R to reload,\n' + 'Cmd+D or shake for dev menu',
  android:
    'Double tap R on your keyboard to reload,\n' +
    'Shake or press menu button for dev menu',
});

type Props = {};
export default class App extends Component<Props> {

  _onOpenContacts() {
    console.log("_onOpenContacts!");
    LoanOnAndroid.getContacts((msg) => {
                                  console.log(msg);
                                },
                                (s) => {
                                  console.log(s);
                                });}
   _onFaceAuth() {
      console.log("_onFaceAuth!");
      LoanOnAndroid.doUdCreditAuth((msg) => {
                                        console.log(msg);
                                      },
                                      (s) => {
                                        console.log(s);
                                      });}

  render() {
    return (
      <View style={styles.container}>
        <Text style={styles.welcome} >Welcome to React Native!</Text>
        <Text style={styles.instructions}>To get started, edit App.js</Text>
        <Text style={styles.instructions}>{instructions}</Text>
        <Text style={styles.welcome} onPress={this._onOpenContacts}>打开通讯录</Text>
        <Text style={styles.welcome} onPress={this._onFaceAuth}>人脸识别</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#F5FCFF',
  },
  welcome: {
    fontSize: 20,
    textAlign: 'center',
    margin: 10,
  },
  instructions: {
    textAlign: 'center',
    color: '#333333',
    marginBottom: 5,
  },
});
