import React, { Component } from 'react';
import './App.css';
import Home from './vehicles/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import VehicleList from './vehicles/VehicleList';
import VehicleEdit from './vehicles/VehicleEdit';

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={Home}/>
            <Route path='/vehicles' exact={true} component={VehicleList}/>
            <Route path='/vehicles/:id' component={VehicleEdit}/>
          </Switch>
        </Router>
    )
  }
}
export default App;