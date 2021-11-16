import React from 'react'
import { ThemeProvider } from 'emotion-theming'
import preset from '@rebass/preset'


export default props =>
  <ThemeProvider theme={preset}>
    {props.children}
  </ThemeProvider>