import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const EntitiesMenu = props => (
  // tslint:disable-next-line:jsx-self-close
  <NavDropdown icon="th-list" name={translate('global.menu.entities.main')} id="entity-menu">
    <MenuItem icon="asterisk" to="/entity/country">
      <Translate contentKey="global.menu.entities.country" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/region">
      <Translate contentKey="global.menu.entities.region" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/city">
      <Translate contentKey="global.menu.entities.city" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/district">
      <Translate contentKey="global.menu.entities.district" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/ward">
      <Translate contentKey="global.menu.entities.ward" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/user-profile">
      <Translate contentKey="global.menu.entities.userProfile" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/user-shipping-address">
      <Translate contentKey="global.menu.entities.userShippingAddress" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-cart">
      <Translate contentKey="global.menu.entities.orderCart" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-history">
      <Translate contentKey="global.menu.entities.orderHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-item">
      <Translate contentKey="global.menu.entities.orderItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-package">
      <Translate contentKey="global.menu.entities.orderPackage" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-package-history">
      <Translate contentKey="global.menu.entities.orderPackageHistory" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-transaction">
      <Translate contentKey="global.menu.entities.orderTransaction" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/order-comment">
      <Translate contentKey="global.menu.entities.orderComment" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/shopping-cart">
      <Translate contentKey="global.menu.entities.shoppingCart" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/shopping-cart-item">
      <Translate contentKey="global.menu.entities.shoppingCartItem" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/warehouse">
      <Translate contentKey="global.menu.entities.warehouse" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/delivery">
      <Translate contentKey="global.menu.entities.delivery" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/entity/delivery-package">
      <Translate contentKey="global.menu.entities.deliveryPackage" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add entities to the menu here */}
  </NavDropdown>
);
