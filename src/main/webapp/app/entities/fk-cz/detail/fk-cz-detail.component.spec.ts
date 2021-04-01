import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { FkCzDetailComponent } from './fk-cz-detail.component';

describe('Component Tests', () => {
  describe('FkCz Management Detail Component', () => {
    let comp: FkCzDetailComponent;
    let fixture: ComponentFixture<FkCzDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [FkCzDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ fkCz: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(FkCzDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FkCzDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load fkCz on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.fkCz).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
