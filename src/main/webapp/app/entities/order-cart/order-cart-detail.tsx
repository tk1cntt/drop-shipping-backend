import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
// tslint:disable-next-line:no-unused-variable
import { Translate, ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './order-cart.reducer';
import { IOrderCart } from 'app/shared/model/order-cart.model';
// tslint:disable-next-line:no-unused-variable
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IOrderCartDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export class OrderCartDetail extends React.Component<IOrderCartDetailProps> {
  componentDidMount() {
    this.props.getEntity(this.props.match.params.id);
  }

  render() {
    const { orderCartEntity } = this.props;
    return (
      <Row>
        <Col md="8">
          <h2>
            <Translate contentKey="dropshippingApp.orderCart.detail.title">OrderCart</Translate> [<b>{orderCartEntity.id}</b>]
          </h2>
          <dl className="jh-entity-details">
            <dt>
              <span id="code">
                <Translate contentKey="dropshippingApp.orderCart.code">Code</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.code}</dd>
            <dt>
              <span id="avatar">
                <Translate contentKey="dropshippingApp.orderCart.avatar">Avatar</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.avatar}</dd>
            <dt>
              <span id="amountDiscount">
                <Translate contentKey="dropshippingApp.orderCart.amountDiscount">Amount Discount</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.amountDiscount}</dd>
            <dt>
              <span id="amountPaid">
                <Translate contentKey="dropshippingApp.orderCart.amountPaid">Amount Paid</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.amountPaid}</dd>
            <dt>
              <span id="depositAmount">
                <Translate contentKey="dropshippingApp.orderCart.depositAmount">Deposit Amount</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.depositAmount}</dd>
            <dt>
              <span id="depositRatio">
                <Translate contentKey="dropshippingApp.orderCart.depositRatio">Deposit Ratio</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.depositRatio}</dd>
            <dt>
              <span id="depositTime">
                <Translate contentKey="dropshippingApp.orderCart.depositTime">Deposit Time</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderCartEntity.depositTime} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="domesticShippingChinaFeeNDT">
                <Translate contentKey="dropshippingApp.orderCart.domesticShippingChinaFeeNDT">Domestic Shipping China Fee NDT</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.domesticShippingChinaFeeNDT}</dd>
            <dt>
              <span id="domesticShippingChinaFee">
                <Translate contentKey="dropshippingApp.orderCart.domesticShippingChinaFee">Domestic Shipping China Fee</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.domesticShippingChinaFee}</dd>
            <dt>
              <span id="domesticShippingVietnamFee">
                <Translate contentKey="dropshippingApp.orderCart.domesticShippingVietnamFee">Domestic Shipping Vietnam Fee</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.domesticShippingVietnamFee}</dd>
            <dt>
              <span id="quantityOrder">
                <Translate contentKey="dropshippingApp.orderCart.quantityOrder">Quantity Order</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.quantityOrder}</dd>
            <dt>
              <span id="quantityPending">
                <Translate contentKey="dropshippingApp.orderCart.quantityPending">Quantity Pending</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.quantityPending}</dd>
            <dt>
              <span id="quantityReceived">
                <Translate contentKey="dropshippingApp.orderCart.quantityReceived">Quantity Received</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.quantityReceived}</dd>
            <dt>
              <span id="rate">
                <Translate contentKey="dropshippingApp.orderCart.rate">Rate</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.rate}</dd>
            <dt>
              <span id="receiverName">
                <Translate contentKey="dropshippingApp.orderCart.receiverName">Receiver Name</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.receiverName}</dd>
            <dt>
              <span id="receiverAddress">
                <Translate contentKey="dropshippingApp.orderCart.receiverAddress">Receiver Address</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.receiverAddress}</dd>
            <dt>
              <span id="receiverMobile">
                <Translate contentKey="dropshippingApp.orderCart.receiverMobile">Receiver Mobile</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.receiverMobile}</dd>
            <dt>
              <span id="receiverNote">
                <Translate contentKey="dropshippingApp.orderCart.receiverNote">Receiver Note</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.receiverNote}</dd>
            <dt>
              <span id="refundAmountByAlipay">
                <Translate contentKey="dropshippingApp.orderCart.refundAmountByAlipay">Refund Amount By Alipay</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.refundAmountByAlipay}</dd>
            <dt>
              <span id="refundAmountByComplaint">
                <Translate contentKey="dropshippingApp.orderCart.refundAmountByComplaint">Refund Amount By Complaint</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.refundAmountByComplaint}</dd>
            <dt>
              <span id="refundAmountByOrder">
                <Translate contentKey="dropshippingApp.orderCart.refundAmountByOrder">Refund Amount By Order</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.refundAmountByOrder}</dd>
            <dt>
              <span id="refundAmountPending">
                <Translate contentKey="dropshippingApp.orderCart.refundAmountPending">Refund Amount Pending</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.refundAmountPending}</dd>
            <dt>
              <span id="shippingChinaVietnamFee">
                <Translate contentKey="dropshippingApp.orderCart.shippingChinaVietnamFee">Shipping China Vietnam Fee</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.shippingChinaVietnamFee}</dd>
            <dt>
              <span id="shippingChinaVietnamFeeDiscount">
                <Translate contentKey="dropshippingApp.orderCart.shippingChinaVietnamFeeDiscount">
                  Shipping China Vietnam Fee Discount
                </Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.shippingChinaVietnamFeeDiscount}</dd>
            <dt>
              <span id="shopAliwang">
                <Translate contentKey="dropshippingApp.orderCart.shopAliwang">Shop Aliwang</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.shopAliwang}</dd>
            <dt>
              <span id="shopId">
                <Translate contentKey="dropshippingApp.orderCart.shopId">Shop Id</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.shopId}</dd>
            <dt>
              <span id="shopLink">
                <Translate contentKey="dropshippingApp.orderCart.shopLink">Shop Link</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.shopLink}</dd>
            <dt>
              <span id="shopName">
                <Translate contentKey="dropshippingApp.orderCart.shopName">Shop Name</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.shopName}</dd>
            <dt>
              <span id="website">
                <Translate contentKey="dropshippingApp.orderCart.website">Website</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.website}</dd>
            <dt>
              <span id="websiteCode">
                <Translate contentKey="dropshippingApp.orderCart.websiteCode">Website Code</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.websiteCode}</dd>
            <dt>
              <span id="websiteLadingCode">
                <Translate contentKey="dropshippingApp.orderCart.websiteLadingCode">Website Lading Code</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.websiteLadingCode}</dd>
            <dt>
              <span id="status">
                <Translate contentKey="dropshippingApp.orderCart.status">Status</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.status}</dd>
            <dt>
              <span id="statusName">
                <Translate contentKey="dropshippingApp.orderCart.statusName">Status Name</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.statusName}</dd>
            <dt>
              <span id="statusStyle">
                <Translate contentKey="dropshippingApp.orderCart.statusStyle">Status Style</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.statusStyle}</dd>
            <dt>
              <span id="tallyFee">
                <Translate contentKey="dropshippingApp.orderCart.tallyFee">Tally Fee</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.tallyFee}</dd>
            <dt>
              <span id="totalAmount">
                <Translate contentKey="dropshippingApp.orderCart.totalAmount">Total Amount</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.totalAmount}</dd>
            <dt>
              <span id="totalAmountNDT">
                <Translate contentKey="dropshippingApp.orderCart.totalAmountNDT">Total Amount NDT</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.totalAmountNDT}</dd>
            <dt>
              <span id="totalPaidByCustomer">
                <Translate contentKey="dropshippingApp.orderCart.totalPaidByCustomer">Total Paid By Customer</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.totalPaidByCustomer}</dd>
            <dt>
              <span id="serviceFee">
                <Translate contentKey="dropshippingApp.orderCart.serviceFee">Service Fee</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.serviceFee}</dd>
            <dt>
              <span id="serviceFeeDiscount">
                <Translate contentKey="dropshippingApp.orderCart.serviceFeeDiscount">Service Fee Discount</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.serviceFeeDiscount}</dd>
            <dt>
              <span id="totalServiceFee">
                <Translate contentKey="dropshippingApp.orderCart.totalServiceFee">Total Service Fee</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.totalServiceFee}</dd>
            <dt>
              <span id="finalAmount">
                <Translate contentKey="dropshippingApp.orderCart.finalAmount">Final Amount</Translate>
              </span>
            </dt>
            <dd>{orderCartEntity.finalAmount}</dd>
            <dt>
              <span id="createAt">
                <Translate contentKey="dropshippingApp.orderCart.createAt">Create At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderCartEntity.createAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <span id="updateAt">
                <Translate contentKey="dropshippingApp.orderCart.updateAt">Update At</Translate>
              </span>
            </dt>
            <dd>
              <TextFormat value={orderCartEntity.updateAt} type="date" format={APP_LOCAL_DATE_FORMAT} />
            </dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderCart.buyer">Buyer</Translate>
            </dt>
            <dd>{orderCartEntity.buyerLogin ? orderCartEntity.buyerLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderCart.chinaStocker">China Stocker</Translate>
            </dt>
            <dd>{orderCartEntity.chinaStockerLogin ? orderCartEntity.chinaStockerLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderCart.vietnamStocker">Vietnam Stocker</Translate>
            </dt>
            <dd>{orderCartEntity.vietnamStockerLogin ? orderCartEntity.vietnamStockerLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderCart.exporter">Exporter</Translate>
            </dt>
            <dd>{orderCartEntity.exporterLogin ? orderCartEntity.exporterLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderCart.createBy">Create By</Translate>
            </dt>
            <dd>{orderCartEntity.createByLogin ? orderCartEntity.createByLogin : ''}</dd>
            <dt>
              <Translate contentKey="dropshippingApp.orderCart.updateBy">Update By</Translate>
            </dt>
            <dd>{orderCartEntity.updateByLogin ? orderCartEntity.updateByLogin : ''}</dd>
          </dl>
          <Button tag={Link} to="/entity/order-cart" replace color="info">
            <FontAwesomeIcon icon="arrow-left" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.back">Back</Translate>
            </span>
          </Button>
          &nbsp;
          <Button tag={Link} to={`/entity/order-cart/${orderCartEntity.id}/edit`} replace color="primary">
            <FontAwesomeIcon icon="pencil-alt" />{' '}
            <span className="d-none d-md-inline">
              <Translate contentKey="entity.action.edit">Edit</Translate>
            </span>
          </Button>
        </Col>
      </Row>
    );
  }
}

const mapStateToProps = ({ orderCart }: IRootState) => ({
  orderCartEntity: orderCart.entity
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(OrderCartDetail);
