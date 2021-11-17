import React, { Component } from 'react';
import './App.css';
import Home from './vehicles/Home';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import VehicleList from './vehicles/VehicleList';
import VehicleEdit from './vehicles/VehicleEdit';
import VehicleView from './vehicles/VehicleView';
import VehicleTripCost from './vehicles/VehicleTripCost';
import VehicleAdd from './vehicles/VehicleAdd';

class App extends Component {
  render() {
    return (
        <Router>
          <Switch>
            <Route path='/' exact={true} component={VehicleList}/>
            <Route path='/vehicles' exact={true} component={VehicleList}/>
            <Route path='/vehicles/new' exact={true} component={VehicleAdd}/>
            <Route path='/vehicles/:id/update' component={VehicleEdit}/>
            <Route path='/vehicles/:id/roadtrip-cost' component={VehicleTripCost}/>
            <Route path='/vehicles/:id' component={VehicleView}/>
            <Route path='/*' component={Home}/>
          </Switch>
        </Router>
    )
  }
}
export default App;