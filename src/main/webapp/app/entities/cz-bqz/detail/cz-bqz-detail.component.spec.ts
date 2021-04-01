import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CzBqzDetailComponent } from './cz-bqz-detail.component';

describe('Component Tests', () => {
  describe('CzBqz Management Detail Component', () => {
    let comp: CzBqzDetailComponent;
    let fixture: ComponentFixture<CzBqzDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [CzBqzDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ czBqz: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(CzBqzDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CzBqzDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load czBqz on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.czBqz).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
