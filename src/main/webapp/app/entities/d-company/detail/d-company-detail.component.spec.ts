import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DCompanyDetailComponent } from './d-company-detail.component';

describe('Component Tests', () => {
  describe('DCompany Management Detail Component', () => {
    let comp: DCompanyDetailComponent;
    let fixture: ComponentFixture<DCompanyDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DCompanyDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ dCompany: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DCompanyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DCompanyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dCompany on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dCompany).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
