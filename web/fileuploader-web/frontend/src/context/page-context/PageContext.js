import React, {createContext, useState, useContext} from "react";

const PageContext = createContext(null);

export default PageContext;
export const usePage = () => useContext(PageContext)
