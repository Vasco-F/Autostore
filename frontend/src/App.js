import logo from './logo.svg';
import './App.css';

import { Component } from "react";

class App extends Component{
  state = {
    vehicles: []
  };

  async componentDidMount(){
    fetch("/vehicles")
      .then(response => response.json())
      .then(data => this.setState({vehicles: data}));
  }

  render(){
    const {vehicles} = this.state;
    return (
      <div className = "App">
        <header className = "App-header">
          <img src={logo} className="App-logo" alt="logo" />
          <div className = "App-intro">
            <h2>Vehicles</h2>
            {vehicles.map(vehicle => 
              <div key={vehicle.id}>
                Manufacturer: {vehicle.manufacturer} | Model: {vehicle.model} | Year: {vehicle.year} | Consumption: {vehicle.consumption}
              </div>
                
            )}
          </div>
        </header>
      </div>
    );
  }
}

export default App;
