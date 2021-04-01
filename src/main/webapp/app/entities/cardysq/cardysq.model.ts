import * as dayjs from 'dayjs';

export interface ICardysq {
  id?: number;
  roomn?: string | null;
  guestname?: string | null;
  account?: string | null;
  rq?: dayjs.Dayjs | null;
  cardid?: string | null;
  djh?: string | null;
  sqh?: string | null;
  empn?: string | null;
  sign?: string | null;
  hoteltime?: dayjs.Dayjs | null;
  yxrq?: dayjs.Dayjs | null;
  je?: number | null;
  ysqmemo?: string | null;
}

export class Cardysq implements ICardysq {
  constructor(
    public id?: number,
    public roomn?: string | null,
    public guestname?: string | null,
    public account?: string | null,
    public rq?: dayjs.Dayjs | null,
    public cardid?: string | null,
    public djh?: string | null,
    public sqh?: string | null,
    public empn?: string | null,
    public sign?: string | null,
    public hoteltime?: dayjs.Dayjs | null,
    public yxrq?: dayjs.Dayjs | null,
    public je?: number | null,
    public ysqmemo?: string | null
  ) {}
}

export function getCardysqIdentifier(cardysq: ICardysq): number | undefined {
  return cardysq.id;
}
