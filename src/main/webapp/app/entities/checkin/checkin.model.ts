import * as dayjs from 'dayjs';

export interface ICheckin {
  id?: number;
  bkid?: number;
  guestId?: number;
  account?: string;
  hoteltime?: dayjs.Dayjs | null;
  indatetime?: dayjs.Dayjs | null;
  residefate?: number | null;
  gotime?: dayjs.Dayjs | null;
  empn?: string | null;
  roomn?: string;
  uname?: string | null;
  rentp?: string;
  protocolrent?: number | null;
  remark?: string | null;
  phonen?: string | null;
  empn2?: string | null;
  adhoc?: string | null;
  auditflag?: number | null;
  groupn?: string | null;
  memo?: string | null;
  lfSign?: string | null;
  keynum?: string | null;
  hykh?: string | null;
  bm?: string | null;
  flag?: number | null;
  jxtime?: dayjs.Dayjs | null;
  jxflag?: number | null;
  checkf?: number | null;
  guestname?: string;
  fgf?: number;
  fgxx?: string | null;
  hourSign?: number | null;
  xsy?: string | null;
  rzsign?: number | null;
  jf?: number | null;
  gname?: string | null;
  zcsign?: number | null;
  cqsl?: number | null;
  sfjf?: number | null;
  ywly?: string | null;
  fk?: string | null;
  fkrq?: dayjs.Dayjs | null;
  bc?: string | null;
  jxremark?: string | null;
  txid?: number | null;
  cfr?: string | null;
  fjbm?: string | null;
  djlx?: string | null;
  wlddh?: string | null;
  fksl?: number | null;
  dqtx?: string | null;
}

export class Checkin implements ICheckin {
  constructor(
    public id?: number,
    public bkid?: number,
    public guestId?: number,
    public account?: string,
    public hoteltime?: dayjs.Dayjs | null,
    public indatetime?: dayjs.Dayjs | null,
    public residefate?: number | null,
    public gotime?: dayjs.Dayjs | null,
    public empn?: string | null,
    public roomn?: string,
    public uname?: string | null,
    public rentp?: string,
    public protocolrent?: number | null,
    public remark?: string | null,
    public phonen?: string | null,
    public empn2?: string | null,
    public adhoc?: string | null,
    public auditflag?: number | null,
    public groupn?: string | null,
    public memo?: string | null,
    public lfSign?: string | null,
    public keynum?: string | null,
    public hykh?: string | null,
    public bm?: string | null,
    public flag?: number | null,
    public jxtime?: dayjs.Dayjs | null,
    public jxflag?: number | null,
    public checkf?: number | null,
    public guestname?: string,
    public fgf?: number,
    public fgxx?: string | null,
    public hourSign?: number | null,
    public xsy?: string | null,
    public rzsign?: number | null,
    public jf?: number | null,
    public gname?: string | null,
    public zcsign?: number | null,
    public cqsl?: number | null,
    public sfjf?: number | null,
    public ywly?: string | null,
    public fk?: string | null,
    public fkrq?: dayjs.Dayjs | null,
    public bc?: string | null,
    public jxremark?: string | null,
    public txid?: number | null,
    public cfr?: string | null,
    public fjbm?: string | null,
    public djlx?: string | null,
    public wlddh?: string | null,
    public fksl?: number | null,
    public dqtx?: string | null
  ) {}
}

export function getCheckinIdentifier(checkin: ICheckin): number | undefined {
  return checkin.id;
}
