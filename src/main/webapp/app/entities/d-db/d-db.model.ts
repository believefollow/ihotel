import * as dayjs from 'dayjs';

export interface IDDb {
  id?: number;
  dbdate?: dayjs.Dayjs;
  dbbillno?: string;
  rdepot?: string;
  cdepot?: string;
  jbr?: string | null;
  remark?: string | null;
  spbm?: string;
  spmc?: string;
  unit?: string | null;
  price?: number | null;
  sl?: number | null;
  je?: number | null;
  memo?: string | null;
  flag?: number | null;
  kcid?: number | null;
  empn?: string | null;
  lrdate?: dayjs.Dayjs | null;
  ckbillno?: string | null;
  f1?: string | null;
  f2?: string | null;
  f1empn?: string | null;
  f2empn?: string | null;
  f1sj?: dayjs.Dayjs | null;
  f2sj?: dayjs.Dayjs | null;
}

export class DDb implements IDDb {
  constructor(
    public id?: number,
    public dbdate?: dayjs.Dayjs,
    public dbbillno?: string,
    public rdepot?: string,
    public cdepot?: string,
    public jbr?: string | null,
    public remark?: string | null,
    public spbm?: string,
    public spmc?: string,
    public unit?: string | null,
    public price?: number | null,
    public sl?: number | null,
    public je?: number | null,
    public memo?: string | null,
    public flag?: number | null,
    public kcid?: number | null,
    public empn?: string | null,
    public lrdate?: dayjs.Dayjs | null,
    public ckbillno?: string | null,
    public f1?: string | null,
    public f2?: string | null,
    public f1empn?: string | null,
    public f2empn?: string | null,
    public f1sj?: dayjs.Dayjs | null,
    public f2sj?: dayjs.Dayjs | null
  ) {}
}

export function getDDbIdentifier(dDb: IDDb): number | undefined {
  return dDb.id;
}
