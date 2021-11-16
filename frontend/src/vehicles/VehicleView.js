import React, {Component} from "react";
import {Button, Container, Form, FormGroup, Input, Label} from "reactstrap";
import AppNavbar from "./AppNavbar";
import {Link, withRouter} from "react-router-dom";
// import {
//     Box,
//     Flex,
//     Button,
//     Text
//   } from 'rebass'
// import {
//     Label,
//     Textarea
//   } from '@rebass/forms'

import "./VehicleView.css";

// import theme from "./Theme";
// import { ThemeProvider } from 'emotion-theming'
// import preset from '@rebass/preset'

// import {ThemeProvider} from '@emotion/react'

class VehicleView extends Component {
    
    emptyItem = {
        manufacturer: "",
        model: "",
        year: 0,
        consumption: 0,
        image_url: ""
    };

    constructor(props){
        super(props);
        this.state = {
            item: this.emptyItem
        };
    }

    async componentDidMount(){
        if(this.props.match.params.id){
            const vehicle = await (await fetch(`/vehicles/${this.props.match.params.id}`)).json();
            this.setState({
                item: vehicle
            })
        }
    }

    render(){
        const {item} = this.state;           

        return <div>
            <AppNavbar/>
            <Container>
                <h2>View Vehicle</h2>
                <img src={item.image} alt="Vehicle" class="vehicle-img"/>
                <Form>
                    <FormGroup>
                        <Label for="manufacturer">Manufacturer</Label>
                        <Input type="text" name="manufacturer" id="manufacturer" value={item.manufacturer || ""}
                            autoComplete="manufacturer" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="model">Model</Label>
                        <Input type="text" name="model" id="model" value={item.model || ""}
                            autoComplete="model" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="year">Year</Label>
                        <Input type="value" name="year" id="year" value={item.year || ""}
                            autoComplete="year" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="consumption">Comsumption</Label>
                        <Input type="value" name="consumption" id="consumption" value={item.consumption || ""}
                            autoComplete="consumption" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="image">Image URL</Label>
                        <Input type="value" name="image" id="image" value={item.image || ""}
                            autoComplete="image" disabled ="disabled"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" tag={Link} to={"/vehicles/" + item.vehicleId + "/update"}>Edit Vehicle</Button>
                        <Button color="secondary" tag={Link} to={"/vehicles/" + item.vehicleId + "/roadtrip-cost"}>Roadtrip Cost Calculator</Button>
                    </FormGroup>
                </Form> 
            </Container>
        </div>
    }

    // render() {
    //     const {item} = this.state;           

    //     return <ThemeProvider theme={preset}>
    //         <Box>
    //             <Flex
    //             px={2}
    //             color='white'
    //             bg='black'
    //             alignItems='center'>
    //                 <Text p={2} fontWeight='bold'>Autostore</Text>
    //                 <Box mx='auto' />
    //                 <Link variant='nav' href='http://localhost:3000/'>
    //                     Home
    //                 </Link>
    //             </Flex>
    //             <Box as="form" py={3}>
    //                 <h2>View Vehicle</h2>
    //                 <img src={item.image} alt="Vehicle" class="vehicle-img"/>
    //                 <Flex mx={-2} mb={3}>
    //                     <Box width={1/6} px={2}>
    //                         <Label htmlFor='manufacturer'>Manufacturer</Label>
    //                         <Textarea
    //                             id='manufacturer'
    //                             name='manufacturer'
    //                             type="text"
    //                             defaultValue={item.manufacturer}
    //                         />
    //                     </Box>
    //                     <Box width={1/6} px={2}>
    //                         <Label htmlFor='model'>Model</Label>
    //                         <Textarea
    //                             id='model'
    //                             name='model'
    //                             type="text"
    //                             defaultValue={item.model}
    //                         />
    //                     </Box>
    //                     <Box width={1/6} px={2}>
    //                         <Label htmlFor='year'>Year</Label>
    //                         <Textarea
    //                             id='year'
    //                             name='year'
    //                             type="text"
    //                             defaultValue={item.year}
    //                         />
    //                     </Box>
    //                     <Box width={1/6} px={2}>
    //                         <Label htmlFor='consumption'>Consumption</Label>
    //                         <Textarea
    //                             id='consumption'
    //                             name='consumption'
    //                             type="value"
    //                             defaultValue={item.consumption}
    //                         />
    //                     </Box>
    //                     <Box width={1/6} px={2}>
    //                         <Label htmlFor='image'>Image URL</Label>
    //                         <Textarea
    //                             id='image'
    //                             name='image'
    //                             type="text"
    //                             defaultValue={item.image}
    //                         />
    //                     </Box>
    //                     <Box width={1/6} px={2}>
    //                         <Button variant='primary' mr={2}>Primary</Button>
    //                         <Button variant="primary" tag={Link} to={"/vehicles/" + item.vehicleId + "/update"}>Edit Vehicle</Button>
    //                         <Button variant="secondary" tag={Link} to={"/vehicles/" + item.vehicleId + "/roadtrip-cost"}>Roadtrip Cost Calculator</Button>
    //                     </Box>
    //                 </Flex>
    //             </Box>
    //         </Box>
    //     </ThemeProvider>
    // }
}
export default withRouter(VehicleView);