import * as dayjs from 'dayjs';

export interface ICheckinBak {
  guestId?: number;
  account?: string;
  hoteltime?: dayjs.Dayjs;
  indatetime?: dayjs.Dayjs | null;
  residefate?: number | null;
  gotime?: dayjs.Dayjs | null;
  empn?: string | null;
  roomn?: string | null;
  uname?: string | null;
  rentp?: string;
  protocolrent?: number | null;
  remark?: string | null;
  comeinfo?: string | null;
  goinfo?: string | null;
  phonen?: string | null;
  empn2?: string | null;
  adhoc?: string | null;
  auditflag?: number | null;
  groupn?: string | null;
  payment?: string | null;
  mtype?: string | null;
  memo?: string | null;
  flight?: string | null;
  credit?: number | null;
  talklevel?: string | null;
  lfSign?: string | null;
  keynum?: string | null;
  icNum?: number | null;
  bh?: number | null;
  icOwner?: string | null;
  markId?: string | null;
  gj?: string | null;
  yfj?: number | null;
  hoteldate?: dayjs.Dayjs | null;
  id?: number;
}

export class CheckinBak implements ICheckinBak {
  constructor(
    public guestId?: number,
    public account?: string,
    public hoteltime?: dayjs.Dayjs,
    public indatetime?: dayjs.Dayjs | null,
    public residefate?: number | null,
    public gotime?: dayjs.Dayjs | null,
    public empn?: string | null,
    public roomn?: string | null,
    public uname?: string | null,
    public rentp?: string,
    public protocolrent?: number | null,
    public remark?: string | null,
    public comeinfo?: string | null,
    public goinfo?: string | null,
    public phonen?: string | null,
    public empn2?: string | null,
    public adhoc?: string | null,
    public auditflag?: number | null,
    public groupn?: string | null,
    public payment?: string | null,
    public mtype?: string | null,
    public memo?: string | null,
    public flight?: string | null,
    public credit?: number | null,
    public talklevel?: string | null,
    public lfSign?: string | null,
    public keynum?: string | null,
    public icNum?: number | null,
    public bh?: number | null,
    public icOwner?: string | null,
    public markId?: string | null,
    public gj?: string | null,
    public yfj?: number | null,
    public hoteldate?: dayjs.Dayjs | null,
    public id?: number
  ) {}
}

export function getCheckinBakIdentifier(checkinBak: ICheckinBak): number | undefined {
  return checkinBak.id;
}
