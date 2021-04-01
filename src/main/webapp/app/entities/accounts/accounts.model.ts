import * as dayjs from 'dayjs';

export interface IAccounts {
  account?: string;
  consumetime?: dayjs.Dayjs;
  hoteltime?: dayjs.Dayjs | null;
  feenum?: number | null;
  money?: number | null;
  memo?: string | null;
  empn?: string | null;
  imprest?: number | null;
  propertiy?: string | null;
  earntypen?: number | null;
  payment?: number | null;
  roomn?: string | null;
  id?: number;
  ulogogram?: string | null;
  lk?: number | null;
  acc?: string | null;
  jzSign?: number | null;
  jzflag?: number | null;
  sign?: string | null;
  bs?: number | null;
  jzhotel?: dayjs.Dayjs | null;
  jzempn?: string | null;
  jztime?: dayjs.Dayjs | null;
  chonghong?: number | null;
  billno?: string | null;
  printcount?: number | null;
  vipjf?: number | null;
  hykh?: string | null;
  sl?: number | null;
  sgdjh?: string | null;
  hoteldm?: string | null;
  isnew?: number | null;
  guestId?: number | null;
  yhkh?: string | null;
  djq?: string | null;
  ysje?: number | null;
  bj?: string | null;
  bjempn?: string | null;
  bjtime?: dayjs.Dayjs | null;
  paper2?: string | null;
  bc?: string | null;
  auto?: string | null;
  xsy?: string | null;
  djkh?: string | null;
  djsign?: string | null;
  classname?: string | null;
  iscy?: string | null;
  bsign?: string | null;
  fx?: string | null;
  djlx?: string | null;
  isup?: number | null;
  yongjin?: number | null;
  czpc?: string | null;
  cxflag?: number | null;
  pmemo?: string | null;
  czbillno?: string | null;
  djqbz?: string | null;
  ysqmemo?: string | null;
  transactionId?: string | null;
  outTradeNo?: string | null;
  gsname?: string | null;
  rz?: dayjs.Dayjs | null;
  gz?: dayjs.Dayjs | null;
  ts?: number | null;
  ky?: string | null;
  xy?: string | null;
  roomtype?: string | null;
  bkid?: number | null;
}

export class Accounts implements IAccounts {
  constructor(
    public account?: string,
    public consumetime?: dayjs.Dayjs,
    public hoteltime?: dayjs.Dayjs | null,
    public feenum?: number | null,
    public money?: number | null,
    public memo?: string | null,
    public empn?: string | null,
    public imprest?: number | null,
    public propertiy?: string | null,
    public earntypen?: number | null,
    public payment?: number | null,
    public roomn?: string | null,
    public id?: number,
    public ulogogram?: string | null,
    public lk?: number | null,
    public acc?: string | null,
    public jzSign?: number | null,
    public jzflag?: number | null,
    public sign?: string | null,
    public bs?: number | null,
    public jzhotel?: dayjs.Dayjs | null,
    public jzempn?: string | null,
    public jztime?: dayjs.Dayjs | null,
    public chonghong?: number | null,
    public billno?: string | null,
    public printcount?: number | null,
    public vipjf?: number | null,
    public hykh?: string | null,
    public sl?: number | null,
    public sgdjh?: string | null,
    public hoteldm?: string | null,
    public isnew?: number | null,
    public guestId?: number | null,
    public yhkh?: string | null,
    public djq?: string | null,
    public ysje?: number | null,
    public bj?: string | null,
    public bjempn?: string | null,
    public bjtime?: dayjs.Dayjs | null,
    public paper2?: string | null,
    public bc?: string | null,
    public auto?: string | null,
    public xsy?: string | null,
    public djkh?: string | null,
    public djsign?: string | null,
    public classname?: string | null,
    public iscy?: string | null,
    public bsign?: string | null,
    public fx?: string | null,
    public djlx?: string | null,
    public isup?: number | null,
    public yongjin?: number | null,
    public czpc?: string | null,
    public cxflag?: number | null,
    public pmemo?: string | null,
    public czbillno?: string | null,
    public djqbz?: string | null,
    public ysqmemo?: string | null,
    public transactionId?: string | null,
    public outTradeNo?: string | null,
    public gsname?: string | null,
    public rz?: dayjs.Dayjs | null,
    public gz?: dayjs.Dayjs | null,
    public ts?: number | null,
    public ky?: string | null,
    public xy?: string | null,
    public roomtype?: string | null,
    public bkid?: number | null
  ) {}
}

export function getAccountsIdentifier(accounts: IAccounts): number | undefined {
  return accounts.id;
}
